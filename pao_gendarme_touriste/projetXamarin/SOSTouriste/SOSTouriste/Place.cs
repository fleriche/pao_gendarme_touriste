using System;
using Xamarin.Forms.Maps;

namespace SOSTouriste
{
	public class Place
	{
		public string Name { get; set; }
		public string Vicinity { get; set; }
		public Position Location { get; set; }
		public Uri Icon { get; set; }
	}
}
