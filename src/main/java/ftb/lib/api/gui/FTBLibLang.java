package ftb.lib.api.gui;

import ftb.lib.mod.FTBLibMod;
import net.minecraft.client.resources.I18n;

public class FTBLibLang
{
	private static String get(String s, Object... o)
	{ return FTBLibMod.mod.translate(s, o); }
	
	public static String button_settings()
	{ return get("button.settings"); }
	
	public static String button_back()
	{ return get("button.back"); }
	
	public static String button_up()
	{ return get("button.up"); }
	
	public static String button_down()
	{ return get("button.down"); }
	
	public static String button_prev()
	{ return get("button.prev"); }
	
	public static String button_next()
	{ return get("button.next"); }
	
	public static String button_cancel()
	{ return get("button.cancel"); }
	
	public static String button_accept()
	{ return get("button.accept"); }
	
	public static String button_add()
	{ return get("button.add"); }
	
	public static String button_remove()
	{ return get("button.remove"); }
	
	public static String button_close()
	{ return get("button.close"); }
	
	public static String button_save()
	{ return get("button.save"); }
	
	public static String button_refresh()
	{ return get("button.refresh"); }
	
	public static String button_edit()
	{ return get("button.edit"); }
	
	public static String client_config()
	{ return I18n.format("client_config"); }
	
	public static String feature_disabled()
	{ return get("feature_disabled"); }
	
	public static String delete_item(String s)
	{ return get("delete_item", s); }
	
	public static String label_server_forced(String s)
	{ return get("label.server_forced", s); }
	
	public static String label_true()
	{ return get("label.true"); }
	
	public static String label_false()
	{ return get("label.false"); }
	
	public static String label_enabled()
	{ return get("label.enabled"); }
	
	public static String label_disabled()
	{ return get("label.disabled"); }
	
	public static String label_enabled(boolean enabled)
	{ return enabled ? label_enabled() : label_disabled(); }
	
	public static String label_on()
	{ return get("label.on"); }
	
	public static String label_off()
	{ return get("label.off"); }
	
	public static String label_online()
	{ return get("label.online"); }
	
	public static String label_offline()
	{ return get("label.offline"); }
	
	public static String label_whitelist()
	{ return get("label.online"); }
	
	public static String label_blacklist()
	{ return get("label.offline"); }
	
	
}