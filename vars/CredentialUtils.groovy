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
  def updateSaadPassword(String username, String newPassword) {
    // Grab all credentials at global level in Jenkins master
    def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
        com.cloudbees.plugins.credentials.common.StandardUsernameCredentials.class,
        Jenkins.instance
    )
    // Search for credential by ID provided
    def c = creds.findResult { it.username == username ? it : null }

    if ( c ) {
      println "found credential ${c.id} for username ${c.username}"
      def credentials_store = Jenkins.instance.getExtensionList(
          'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
          )[0].getStore()
      def result = credentials_store.updateCredentials(
          com.cloudbees.plugins.credentials.domains.Domain.global(), 
          c, 
          new UsernamePasswordCredentialsImpl(c.scope, c.id, c.description, c.username, newPassword)
          )
      
      if (result) {
          println "password changed for ${username}"
	  print "SAAAAAAAAAAAAAD FROM GROOVY"
      } else {
          println "failed to change password for ${username}"
      }
    } else {
      println "could not find credential for ${username}"
    }
  }

}
