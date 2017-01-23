using System;
using System.Collections.Generic;
using SOSTouriste.Resources;
using SOSTouriste.Helpers;

using Xamarin.Forms;

namespace SOSTouriste
{

	public partial class LanguagePage : ContentPage
	{
		public ListView mLanguages { get { return langListView; } }

		public LanguagePage()
		{
			InitializeComponent();

			var languages = new List<Language>();

			languages.Add(new Language
			{
				mLanguage = LanguageResources.french,
				mLangIso = "fr"
			});

			languages.Add(new Language
			{
				mLanguage = LanguageResources.english,
				mLangIso = "en"
			});

			languages.Add(new Language
			{
				mLanguage = LanguageResources.german,
				mLangIso = "de"
			});

			languages.Add(new Language
			{
				mLanguage = LanguageResources.italian,
				mLangIso = "it"
			});

			languages.Add(new Language
			{
				mLanguage = LanguageResources.netherlands,
				mLangIso = "nl"
			});

			languages.Add(new Language
			{
				mLanguage = LanguageResources.spanish,
				mLangIso = "es"
			});

			languages.Add(new Language
			{
				mLanguage = LanguageResources.polish,
				mLangIso = "pl"
			});

			languages.Add(new Language
			{
				mLanguage = LanguageResources.portuguese,
				mLangIso = "pt"
			});
			langListView.ItemsSource = languages;
			langListView.ItemSelected += OnItemSelected;
		}

		public void OnItemSelected(object sender, SelectedItemChangedEventArgs e)
		{
			var item = e.SelectedItem as Language;
			if (item != null)
			{
				Settings.DisplayLanguage = item.mLangIso;
				System.Globalization.CultureInfo ci = new System.Globalization.CultureInfo(Settings.DisplayLanguage);
				LanguageResources.Culture = ci;
				App app = Application.Current as App;
				app.MainPage = new MenuPage();
			}
		}
	}
}
