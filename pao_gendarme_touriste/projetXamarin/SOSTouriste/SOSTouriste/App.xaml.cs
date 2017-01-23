using Xamarin.Forms;
using SOSTouriste.Helpers;
using SOSTouriste.Resources;

namespace SOSTouriste {
	
	public partial class App : Application {
		
		public App() {
			System.Globalization.CultureInfo ci = new System.Globalization.CultureInfo(Settings.DisplayLanguage);
			LanguageResources.Culture = ci;
			MainPage = new MenuPage();
		}

		protected override void OnStart() {
			// Handle when your app starts
		}

		protected override void OnSleep() {
			// Handle when your app sleeps
		}

		protected override void OnResume() {
			// Handle when your app resumes
		}
	}
}
