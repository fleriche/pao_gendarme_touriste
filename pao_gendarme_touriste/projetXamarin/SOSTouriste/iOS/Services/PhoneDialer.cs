using System;
using Foundation;
using SOSTouriste.iOS;
using UIKit;

[assembly: Xamarin.Forms.Dependency(typeof(PhoneDialer))]
namespace SOSTouriste.iOS {
	
	public class PhoneDialer : IDialer {
		
		public bool dial(string number) {
			return UIApplication.SharedApplication.OpenUrl(
				new NSUrl("tel:" + number));
		}
	}
}
