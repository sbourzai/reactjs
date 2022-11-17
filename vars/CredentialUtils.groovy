package com.jenkins.creds.scripts

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

import java.io.Serializable

import javax.naming.Context
import javax.naming.NamingEnumeration
import javax.naming.directory.Attributes
import javax.naming.directory.BasicAttribute
import javax.naming.directory.DirContext
import javax.naming.directory.ModificationItem
import javax.naming.directory.SearchResult
import javax.naming.directory.InvalidAttributeValueException
import javax.naming.ldap.InitialLdapContext
import javax.naming.ldap.LdapContext

import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import com.cloudbees.hudson.plugins.folder.Folder
import com.cloudbees.hudson.plugins.folder.properties.*
import com.cloudbees.hudson.plugins.folder.properties.FolderCredentialsProvider.FolderCredentialsProperty

class CredentialUtils implements Serializable {

  // If no folder name is included, search for credential at global level
  def rotatePassword(String cid, int passwordLength = 12, String directoryServer = 'AD') {

    // Grab all credentials at global level in Jenkins master
    def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
      com.cloudbees.plugins.credentials.common.StandardUsernameCredentials.class, Jenkins.instance)

    // Search for credential by ID provided
    def c = creds.findResult { it.id == cid ? it : null }

    // If credential is found
    if (c) {

      // Generate a new random password
      def newPassword = generatePassword(passwordLength)

      // Change the password in data store
      try {
        if (directoryServer.equalsIgnoreCase('AD')) {
          changeActiveDirPassword(c.username, "${c.password}", newPassword)
        } else if (directoryServer.equalsIgnoreCase('LDAP')) {
          changeLdapPassword(c.username, "${c.password}", newPassword)
        } else {
          throw new IllegalArgumentException("Directory server must be either 'AD' or 'LDAP'")
        }
      } catch (Exception e) {
        if (e.getMessage().indexOf('CONSTRAINT_ATT_TYPE') > 0) {
          throw new IllegalStateException('Password can only be changed once every 24 hours')
        }
        throw new IllegalStateException("Failed to update password in '${directoryServer}' for username '${c.username}'. ", e)
      }
      
      def credentials_store = Jenkins.instance.getExtensionList(
        'com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

      // Update existing credential in Jenkins to new password
      def result = credentials_store.updateCredentials(
        com.cloudbees.plugins.credentials.domains.Domain.global(),
        c,
        new UsernamePasswordCredentialsImpl(c.scope, c.id, c.description, c.username, newPassword))

      if (!result) {
        throw new IllegalStateException("Password change in data store (AD/LDAP) but failed to update Credentials Store. New password in AD/LDAP is '${newPassword}'")
      }

      return newPassword
    } else {
      throw new IllegalArgumentException("Could not find credentials for $cid")
    }
  }

  // If folder name is included, search at folder level
  def rotatePassword(String cid, String fullFolderName, int passwordLength = 12, String directoryServer = 'AD') {

    // Get folder in Jenkins with name provided
    def folder = Jenkins.instance.getAllItems(Folder.class).findResult { it.fullName == fullFolderName ? it : null }
    if(!folder) {
      throw new IllegalArgumentException("Could not find folder: ${fullFolderName}")
    }

    // Look for credential with ID provided inside folder
    def creds = com.cloudbees.hudson.plugins.folder.properties.FolderCredentialsProvider.lookupCredentials(
      com.cloudbees.plugins.credentials.common.StandardUsernameCredentials.class, folder)

    def c = creds.findResult { it.id == cid ? it : null }

    // If credential is found
    if (c) {

      // Generate a new random password
      def newPassword = generatePassword(passwordLength)

      // Change the password in Data store (Active Directory / LDAP)
      try {
        if (directoryServer.equalsIgnoreCase('AD')) {
          changeActiveDirPassword(c.username, "${c.password}", newPassword)
        } else if (directoryServer.equalsIgnoreCase('LDAP')) {
          changeLdapPassword(c.username, "${c.password}", newPassword)
        } else {
          throw new IllegalArgumentException("directoryServer must be either 'AD' or 'LDAP'")
        }
      } catch (Exception e) {
        if (e.getMessage().indexOf('CONSTRAINT_ATT_TYPE') > 0) {
          throw new IllegalStateException('Password can only be changed once every 24 hours')
        }
        throw new IllegalStateException("Failed to update password in '${directoryServer}' for username '${c.username}'. ", e)
      }

      FolderCredentialsProperty property = folder.getProperties().get(FolderCredentialsProperty.class)

      // Update credential in Jenkins with new password
      def result = property.getStore().updateCredentials(
        com.cloudbees.plugins.credentials.domains.Domain.global(),
        c,
        new UsernamePasswordCredentialsImpl(c.scope, c.id, c.description, c.username, newPassword))

      if (!result) {
        throw new IllegalStateException("Password change in data store (AD/LDAP) but failed to update Credentials Store. New password in AD/LDAP is '${newPassword}'")
      }

      return newPassword
    } else {
      throw new IllegalArgumentException("Could not find credentials for $cid")
    }
  }

  // Generate random password
  def generatePassword(int size) {
    Random rnd = new Random()
    def password = ''
    def pool = [
      'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
      'abcdefghijklmnopqrstuvwxyz',
      '0123456789',
      '!^*']

    for (int i = 0; i < size; i++) {
      def chars = pool[i % 4]
      password += chars[rnd.nextInt(chars.length() - 1)]
    }

    return password
  }

  // Change password in Active Directory
  def changeActiveDirPassword(String username, String oldPassword, String newPassword) {
    def ldapUrl = 'ldaps://____________:636'    // TODO: replace this with AD URL
    def distinguishedName = "cn=${username},ou=Users,ou=SysMgt,dc=us,dc=ups,dc=com".toString()

    def Hashtable env = new Hashtable<String, String>()
      env.put(Context.INITIAL_CONTEXT_FACTORY, 'com.sun.jndi.ldap.LdapCtxFactory')
      env.put(Context.PROVIDER_URL, ldapUrl)
      env.put(Context.SECURITY_AUTHENTICATION, 'simple')
      env.put(Context.SECURITY_PRINCIPAL, distinguishedName)
      env.put(Context.SECURITY_CREDENTIALS, oldPassword)

    LdapContext ctx = new InitialLdapContext(env, null)

    def mods = [
      new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute('unicodePwd', encodePassword(oldPassword))),
      new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute('unicodePwd', encodePassword(newPassword)))] as ModificationItem[]

    ctx.modifyAttributes(distinguishedName, mods)
  }

  def changeLdapPassword(String username, String oldPassword, String newPassword) {
    def ldapUrl = 'ldaps://____________:636'    // TODO: replace this with LDAP URL
    def distinguishedName = "uid=${username},ou=AppAccounts,ou=People,ou=Entsys,dc=ups.com".toString()

    def Hashtable env = new Hashtable<String, String>()
      env.put(Context.INITIAL_CONTEXT_FACTORY, 'com.sun.jndi.ldap.LdapCtxFactory')
      env.put(Context.PROVIDER_URL, ldapUrl)
      env.put(Context.SECURITY_AUTHENTICATION, 'simple')
      env.put(Context.SECURITY_PRINCIPAL, distinguishedName)
      env.put(Context.SECURITY_CREDENTIALS, oldPassword)
      env.put(Context.REFERRAL, "follow");

    LdapContext ctx = new InitialLdapContext(env, null)

    def mods = [
      new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", newPassword))
	  ] as ModificationItem[]

    ctx.modifyAttributes(distinguishedName, mods)
  }

  def encodePassword(String password) {
    return "\"$password\"".getBytes('UTF-16LE')
  }

}
