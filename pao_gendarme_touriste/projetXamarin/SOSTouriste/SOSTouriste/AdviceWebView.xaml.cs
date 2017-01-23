using System;
using System.Collections.Generic;
using System.IO;
using Xamarin.Forms;
using SOSTouriste.Resources;
using SOSTouriste.Helpers;

namespace SOSTouriste
{
	public partial class AdviceWebView : ContentPage
	{
		public AdviceWebView(int number)
		{
			InitializeComponent();

			var baseUrl = DependencyService.Get<IBaseUrl>().get();
			var lang = Settings.DisplayLanguage;
			var fileName = lang+"/advices/advice_"+number+"_"+lang.ToUpper()+".html";

			string url = Path.Combine(baseUrl, fileName);
			webview.Source = url;
		}
	}
}
