using System;
using System.Collections.Generic;
using SOSTouriste.Resources;

using Xamarin.Forms;

namespace SOSTouriste {
	
	public partial class MasterPage : ContentPage {
		
		public ListView mDrawerItems { get { return drawerListView; } }

		public MasterPage() {
			InitializeComponent();

			var drawerItems = new List<MasterItem>();

			drawerItems.Add(new MasterItem {
				mTitle = LanguageResources.accueil,
				mIconSource = "home.png",
				mTargetType = typeof(HomePage)
			});

			drawerItems.Add(new MasterItem {
				mTitle = LanguageResources.title_activity_language,
				mIconSource = "language.png",
				mTargetType = typeof(LanguagePage)
			});

			drawerListView.ItemsSource = drawerItems;
		}
	}
}
