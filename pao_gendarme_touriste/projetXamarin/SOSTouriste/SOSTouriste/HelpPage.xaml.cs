using System;
using System.Collections.Generic;
using System.IO;
using Xamarin.Forms;
using SOSTouriste.Resources;
using SOSTouriste.Helpers;

namespace SOSTouriste {
	public partial class HelpPage : ContentPage {
		public HelpPage() {
			InitializeComponent();
			var baseUrl = DependencyService.Get<IBaseUrl>().get();
			var lang = Settings.DisplayLanguage;
			var fileName = lang + "/help.html";

			string url = Path.Combine(baseUrl, fileName);
			webviewHelp.Source = url;
		}
	}
}
