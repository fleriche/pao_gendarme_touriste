// Helpers/Settings.cs
using System.Collections.Generic;
using Plugin.Settings;
using Plugin.Settings.Abstractions;

namespace SOSTouriste.Helpers
{
	/// <summary>
	/// This is the Settings static class that can be used in your Core solution or in any
	/// of your client applications. All settings are laid out the same exact way with getters
	/// and setters. 
	/// </summary>
	public static class Settings
	{
		private static ISettings AppSettings
		{
			get
			{
				return CrossSettings.Current;
			}
		}

		#region Setting Constants

		private const string SettingsKey = "settings_key";
		private static readonly string SettingsDefault = string.Empty;

		private const string DisplayLanguageKey = "display_language_key";
		private static readonly string DisplayLanguageDefault = "en";

		private const string nameProfileKey = "name_profile_key";
		private static readonly string nameProfileDefault = "name";

		private const string firstnameProfileKey = "firstname_profile_key";
		private static readonly string firstnameProfileDefault = "firstname";

		private const string bloodtypeProfileKey = "bloodtype_profile_key";
		private static readonly string bloodtypeProfileDefault = "O+";

		#endregion


		public static string GeneralSettings
		{
			get
			{
				return AppSettings.GetValueOrDefault<string>(SettingsKey, SettingsDefault);
			}
			set
			{
				AppSettings.AddOrUpdateValue<string>(SettingsKey, value);
			}
		}

		public static string DisplayLanguage
		{
			get
			{
				return AppSettings.GetValueOrDefault<string>(DisplayLanguageKey, DisplayLanguageDefault);
			}
			set
			{
				AppSettings.AddOrUpdateValue<string>(DisplayLanguageKey, value);
			}
		}

		public static string nameProfile
		{
			get
			{
				return AppSettings.GetValueOrDefault<string>(nameProfileKey, nameProfileDefault);
			}
			set
			{
				AppSettings.AddOrUpdateValue<string>(nameProfileKey, value);
			}
		}

		public static string firstnameProfile
		{
			get
			{
				return AppSettings.GetValueOrDefault<string>(firstnameProfileKey, firstnameProfileDefault);
			}
			set
			{
				AppSettings.AddOrUpdateValue<string>(firstnameProfileKey, value);
			}
		}

		public static string bloodtypeProfile
		{
			get
			{
				return AppSettings.GetValueOrDefault<string>(bloodtypeProfileKey, bloodtypeProfileDefault);
			}
			set
			{
				AppSettings.AddOrUpdateValue<string>(bloodtypeProfileKey, value);
			}
		}
	}
}