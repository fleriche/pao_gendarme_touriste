using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace SOSTouriste {
	
	public partial class MenuPage : MasterDetailPage {
		
		public MenuPage() {
			InitializeComponent();

			masterPage.mDrawerItems.ItemSelected += OnItemSelected;
			//masterPage.ToolbarItems.Add(new ToolbarItem());
		}

		void OnItemSelected(object sender, SelectedItemChangedEventArgs e) {
			var item = e.SelectedItem as MasterItem;
			if (item != null) {
				NavigationPage navPage = new NavigationPage((Page)Activator.CreateInstance(item.mTargetType));
				navPage.BarBackgroundColor = Color.FromHex("#28354F");
				Detail = navPage;
				masterPage.mDrawerItems.SelectedItem = null;
				IsPresented = false;
			}
		}

		async void onHelpClicked(object sender, EventArgs e) { 
			await Navigation.PushModalAsync(new HelpPage());
		}
	}
}
