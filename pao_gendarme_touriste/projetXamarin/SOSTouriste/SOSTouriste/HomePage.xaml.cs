using System;
using System.Collections.Generic;
using SOSTouriste.Resources;
using SOSTouriste.Helpers;
using Xamarin.Forms;
using XLabs.Forms.Controls;

namespace SOSTouriste {

	public partial class HomePage : ContentPage {
		private String mEmergencyNumber = "112";

		public HomePage() {
			InitializeComponent();

			var tapImgEmergency = new TapGestureRecognizer();
			var tapContacts = new TapGestureRecognizer();
			var tapAdvice = new TapGestureRecognizer();
			var tapLocation = new TapGestureRecognizer();
			var tapProfile = new TapGestureRecognizer();

			tapImgEmergency.Tapped += onEmergencyClicked;
			imgEmergency.GestureRecognizers.Add(tapImgEmergency);

			tapContacts.Tapped += onContactsClicked;
			imgContact.GestureRecognizers.Add(tapContacts);

			tapAdvice.Tapped += onAdviceClicked;
			imgAdvice.GestureRecognizers.Add(tapAdvice);

			tapLocation.Tapped += onLocationClicked;
			imgLocation.GestureRecognizers.Add(tapLocation);

			tapProfile.Tapped += onProfileClicked;
			imgProfile.GestureRecognizers.Add(tapProfile);
		}

		public void onEmergencyClicked(object sender, EventArgs e) {
			/*if (await this.DisplayAlert(
							 "Dial a Number",
							 "Call " + mEmergencyNumber + "?",
							 "Yes",
							 "No")) {*/

			var dialer = DependencyService.Get<IDialer>();
			if (dialer != null) {
				dialer.dial(mEmergencyNumber);
			}
			//}
		}

		public async void onContactsClicked(object sender, EventArgs e) {
			await Navigation.PushAsync(new ContactsPage());
		}

		public async void onAdviceClicked(object sender, EventArgs e) {
			await Navigation.PushAsync(new AdvicesPage());
		}

		public async void onLocationClicked(object sender, EventArgs e) {
			await Navigation.PushAsync(new LocationPage());
		}

		public async void onProfileClicked(object sender, EventArgs e) {
			await Navigation.PushAsync(new ProfilePage());
		}
	}
}
