using System;
using System.Collections.Generic;
using SOSTouriste.Helpers;
using Xamarin.Forms;

namespace SOSTouriste {
	public partial class ProfilePage : ContentPage {
		Entry nameEntry;
		Entry firstnameEntry;
		Label nameLabel;
		Label firstnameLabel;
		Label bloodtypeLabel;
		Picker bloodPicker;
		Boolean entryVisible = false;
		Dictionary<string, int> bloodType = new Dictionary<string, int>
		{
			{ "O+", 0 }, { "O-", 1 },
			{ "A+", 2 }, { "A-", 3 },
			{ "B+", 4 }, { "B-", 5 },
			{ "AB+", 6 }, { "AB-", 7 }
		};

		public ProfilePage() {
			InitializeComponent();
			nameEntry = entryName;
			firstnameEntry = entryFirstname;
			nameLabel = labelName;
			firstnameLabel = labelFirstname;
			bloodtypeLabel = labelBloodtype;
			bloodPicker = pickerBlood;

			foreach (string blood in bloodType.Keys) {
				bloodPicker.Items.Add(blood);
			}

			nameLabel.Text = Settings.nameProfile;
			firstnameLabel.Text = Settings.firstnameProfile;
			bloodtypeLabel.Text = Settings.bloodtypeProfile;
		}

		public void ItemClicked(object sender, EventArgs e) {
			if (entryVisible) {
				nameEntry.IsVisible = false;
				firstnameEntry.IsVisible = false;
				bloodPicker.IsVisible = false;

				Settings.nameProfile = nameEntry.Text;
				Settings.firstnameProfile = firstnameEntry.Text;
				Settings.bloodtypeProfile = bloodPicker.Items[bloodPicker.SelectedIndex];

				nameLabel.Text = Settings.nameProfile;
				firstnameLabel.Text = Settings.firstnameProfile;
				bloodtypeLabel.Text = Settings.bloodtypeProfile;

				nameLabel.IsVisible = true;
				firstnameLabel.IsVisible = true;
				bloodtypeLabel.IsVisible = true;
				entryVisible = false;
			} else {
				nameEntry.Text = nameLabel.Text;
				firstnameEntry.Text = firstnameLabel.Text;
				bloodPicker.SelectedIndex = bloodType[bloodtypeLabel.Text];

				nameEntry.IsVisible = true;
				firstnameEntry.IsVisible = true;
				nameLabel.IsVisible = false;
				firstnameLabel.IsVisible = false;
				bloodtypeLabel.IsVisible = false;
				bloodPicker.IsVisible = true;
				entryVisible = true;
			}
		}
	}
}
