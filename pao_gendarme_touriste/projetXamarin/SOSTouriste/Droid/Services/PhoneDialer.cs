using System;
using System.Linq;
using Android.Content;
using Uri = Android.Net.Uri;
using Android.Telephony;
using Xamarin.Forms;
using SOSTouriste.Droid;

[assembly: Xamarin.Forms.Dependency(typeof(PhoneDialer))]
namespace SOSTouriste.Droid {
	
	public class PhoneDialer : IDialer {
		
		public bool dial(String phoneNumber) {
			var context = Forms.Context;
			if (context == null)
				return false;
			
			var uri = Android.Net.Uri.Parse("tel:" + phoneNumber);
			var intent = new Intent(Intent.ActionDial, uri);

			if (IsIntentAvailable(context, intent)) {
				context.StartActivity(intent);
				return true;
			}

			return false;
		}

		public static bool IsIntentAvailable(Context context, Intent intent) {
			var packageManager = context.PackageManager;

			var list = packageManager.QueryIntentServices(intent, 0)
				.Union(packageManager.QueryIntentActivities(intent, 0));
			if (list.Any())
				return true;

			TelephonyManager mgr = TelephonyManager.FromContext(context);
			return mgr.PhoneType != PhoneType.None;
		}
	}
}