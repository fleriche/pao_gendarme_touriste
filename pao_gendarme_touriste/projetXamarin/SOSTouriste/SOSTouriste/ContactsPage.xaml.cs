using System;
using System.Collections.Generic;
using SOSTouriste.Resources;

using Xamarin.Forms;

namespace SOSTouriste {
	public partial class ContactsPage : ContentPage {
		public ListView mContacts { get { return contactsListView; } }

		public ContactsPage() {
			InitializeComponent();

			var contacts = new List<Contacts>();

			contacts.Add(new Contacts {
				mText = LanguageResources.emergency,
				mIcon = "emer.png",
				mNumber = "112"
			});

			contacts.Add(new Contacts
			{
				mText = LanguageResources.fireman,
				mIcon = "fireman.png",
				mNumber = "18"
			});

			contacts.Add(new Contacts
			{
				mText = LanguageResources.samu,
				mIcon = "samu.png",
				mNumber = "15"
			});

			contacts.Add(new Contacts
			{
				mText = LanguageResources.police,
				mIcon = "police.png",
				mNumber = "17"
			});

			contactsListView.ItemsSource = contacts;
			contactsListView.ItemSelected += OnItemSelected;
		}

		public void OnItemSelected(object sender, SelectedItemChangedEventArgs e) {
			var item = e.SelectedItem as Contacts;
			if (item != null) {
				/*if (await this.DisplayAlert(
							 "Dial a Number",
							 "Call " + item.mNumber + "?",
							 "Yes",
							 "No")) {*/

					var dialer = DependencyService.Get<IDialer>();
					if (dialer != null) {
						dialer.dial(item.mNumber);
					}
				//}
			}
		}
	}
}
