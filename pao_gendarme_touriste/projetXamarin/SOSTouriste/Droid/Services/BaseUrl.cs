using System;
using System.Linq;
using Android.Content;
using Uri = Android.Net.Uri;
using Android.Telephony;
using Xamarin.Forms;
using SOSTouriste.Droid;

[assembly: Xamarin.Forms.Dependency(typeof(BaseUrl))]
namespace SOSTouriste.Droid
{

	public class BaseUrl : IBaseUrl
	{
		public string get()
		{
			return "file:///android_asset/";
		}
	}
}