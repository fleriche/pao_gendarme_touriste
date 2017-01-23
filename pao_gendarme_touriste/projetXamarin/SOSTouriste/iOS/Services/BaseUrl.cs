using System;
using Foundation;
using SOSTouriste.iOS;

[assembly: Xamarin.Forms.Dependency(typeof(BaseUrl))]
namespace SOSTouriste.iOS
{

	public class BaseUrl : IBaseUrl
	{

		public string get()
		{
			return NSBundle.MainBundle.BundlePath+"/assets/";
		}
	}
}
