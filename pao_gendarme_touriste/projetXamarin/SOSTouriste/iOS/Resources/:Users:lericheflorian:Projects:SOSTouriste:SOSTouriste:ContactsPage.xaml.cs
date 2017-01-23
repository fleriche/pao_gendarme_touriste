using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace SOSTouriste {
	public partial class ContactsPage : ContentPage {
		public ListView mContacts { get { return contactsListView; } }

		public ContactsPage() {
			InitializeComponent();

			var mContacts = new List<Contacts>();

			mContacts.Add(new Contacts {
				mText = "Accueil",
				mIcon = "emer.png"
			});

			mContacts.Add(new Contacts
			{
				mText = "Accueil",
				mIcon = "fireman.png"
			});

			mContacts.Add(new Contacts
			{
				mText = "Accueil",
				mIcon = "samu.png"
			});

			mContacts.Add(new Contacts
			{
				mText = "Accueil",
				mIcon = "police.png"
			});

			contactsListView.ItemsSource = mContacts;
		}
	}
}
