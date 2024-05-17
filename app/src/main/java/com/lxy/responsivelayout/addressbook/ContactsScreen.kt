package com.lxy.responsivelayout.addressbook

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 *
 * @Author：liuxy
 * @Date：2024/5/17 17:08
 * @Desc：
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(contacts: List<Contact> = contactsList) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            ContactListWithIndex(contacts)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactList(contacts: List<Contact>) {
    val groupedContacts = contacts.groupBy { it.name.first().uppercaseChar() }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        groupedContacts.forEach { (initial, contactsForInitial) ->
            stickyHeader {
                Text(
                    text = initial.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            itemsIndexed(contactsForInitial) { _, contact ->
                ContactItem(contact)
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Text(
        text = contact.name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun ContactListWithIndex(contacts: List<Contact>) {
    Box(Modifier.fillMaxSize()) {
        ContactList(contacts)
        AlphabetIndex(letters = contacts.map { it.name.first().uppercaseChar() }.distinct())
    }
}

@Composable
fun AlphabetIndex(
    modifier: Modifier = Modifier,
    letters: List<Char>,
    
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .wrapContentWidth()
            .padding(end = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        letters.forEach { letter ->
            Text(
                text = letter.toString(),
                modifier = Modifier
                    .padding(vertical = 4.dp)
            )
        }
    }
}



val contactsList = listOf(
    Contact("Alice"),
    Contact("Alice"),
    Contact("Alice"),
    Contact("Alice"),
    Contact("Alice"),
    Contact("Bob"),
    Contact("Bob"),
    Contact("Bob"),
    Contact("Bob"),
    Contact("Bob"),
    Contact("Bob"),
    Contact("Charlie"),
    Contact("Charlie"),
    Contact("Charlie"),
    Contact("Charlie"),
    Contact("Charlie"),
    Contact("Charlie"),
    Contact("Dharlie"),
    Contact("Dharlie"),
    Contact("Dharlie"),
    Contact("Dharlie"),
    Contact("Dharlie"),
    Contact("Eharlie"),
    Contact("Eharlie"),
    Contact("Eharlie"),
    Contact("Eharlie"),
    Contact("Eharlie"),
    Contact("Eharlie"),
    Contact("Eharlie"),
    // Add more contacts
).sortedBy { it.name }
