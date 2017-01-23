using System;
using System.Collections.Generic;
using SOSTouriste.Resources;

using Xamarin.Forms;

namespace SOSTouriste {
	public partial class LocationPage : ContentPage {
		public ListView mContacts { get { return locationListView; } }
		public LocationPage() {
			InitializeComponent();

			var locations = new List<Location>();

			locations.Add(new Location {
				mText = LanguageResources.hospital,
				mIcon = "samu.png",
				mAddressUri = "http://maps.google.com/maps?q="+LanguageResources.hospital+"&directionsmode=transit"
			});

			locations.Add(new Location {
				mText = LanguageResources.police,
				mIcon = "police.png",
				mAddressUri = "http://maps.google.com/maps?q="+LanguageResources.police+"&directionsmode=transit"
			});

			locations.Add(new Location {
				mText = LanguageResources.consulate,
				mIcon = "consulate.png",
				mAddressUri = "http://maps.google.com/maps?q="+LanguageResources.consulate+"&directionsmode=transit"
			});

			locationListView.ItemsSource = locations;
			locationListView.ItemSelected += OnItemSelected;
		}

		public void OnItemSelected(object sender, SelectedItemChangedEventArgs e) {
			var item = e.SelectedItem as Location;
			if (item != null) {
				var uri = new Uri(item.mAddressUri);
				Device.OpenUri(uri);
			}
		}
	}
}
