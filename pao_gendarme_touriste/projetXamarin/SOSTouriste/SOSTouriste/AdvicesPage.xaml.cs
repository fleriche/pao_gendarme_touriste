using System;
using System.Collections.Generic;
using SOSTouriste.Resources;
using System.Collections;

using Xamarin.Forms;

namespace SOSTouriste
{
	public partial class AdvicesPage : ContentPage
	{
		public ListView mAdvices { get { return advicesListView; } }

		public AdvicesPage()
		{
			InitializeComponent();

			var advicesLabels = getAdviceLabel(LanguageResources.adviceLabel);
			var advices = new List<Advice>();
			var i = 0;

			foreach (String advice in advicesLabels)
			{
				advices.Add(new Advice
				{
					mText = advice,
					mIcon = "image_advice_"+i+".png",
					mNumber = i
				});
				i++;
			}

			advicesListView.ItemsSource = advices;
			advicesListView.ItemSelected += OnItemSelected;
		}

		private String[] getAdviceLabel(String adviceString)
		{
			return adviceString.Split('|');
		}

		public async void OnItemSelected(object sender, SelectedItemChangedEventArgs e)
		{
			var item = e.SelectedItem as Advice;
			if (item != null)
			{
				await Navigation.PushAsync(new AdviceWebView(item.mNumber));
			}
		}
	}
}
