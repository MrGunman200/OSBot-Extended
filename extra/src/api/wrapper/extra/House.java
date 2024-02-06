package api.wrapper.extra;

import api.data.vars.Varbits;
import api.provider.ExtraProvider;
import api.provider.ExtraProviders;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.MethodProvider;

public class House
{

	public static final Area RIMMINGTON_PORTAL = new Area(2946, 3228, 2960, 3218);
	public static final Area TAVERLY_POH_PORTAL = new Area(2946, 3228, 2960, 3218);
	public static final Area POLLNIVNEACH_POH_PORTAL = new Area(2946, 3228, 2960, 3218);
	public static final Area RELLEKKA_POH_PORTAL = new Area(2946, 3228, 2960, 3218);
	public static final Area BRIMHAVEN_POH_PORTAL = new Area(2946, 3228, 2960, 3218);
	public static final Area YANILLE_POH_PORTAL = new Area(2946, 3228, 2960, 3218);
	public static final Area PRIFDDINAS_POH_PORTAL = new Area(2946, 3228, 2960, 3218);
	public static final Area HOSIDIUS_POH_PORTAL = new Area(2946, 3228, 2960, 3218);

	public static final Area[] HOUSE_LOCATIONS = {
		RIMMINGTON_PORTAL,
		TAVERLY_POH_PORTAL,
		POLLNIVNEACH_POH_PORTAL,
		RELLEKKA_POH_PORTAL,
		BRIMHAVEN_POH_PORTAL,
		YANILLE_POH_PORTAL,
		PRIFDDINAS_POH_PORTAL,
		HOSIDIUS_POH_PORTAL
	};

	public static Position getOutsideLocation()
	{
		final ExtraProvider ctx = ExtraProviders.getContext();
		return getOutsideLocation(ctx);
	}

	public static Position getOutsideLocation(MethodProvider ctx)
	{
		if (!ctx.getClient().isLoggedIn())
		{
			return null;
		}

		int idx = Varbits.HOUSE_LOCATION.getValue(ctx);
		if (idx >= HOUSE_LOCATIONS.length || idx <= 0)
		{
			return null;
		}

		return HOUSE_LOCATIONS[idx - 1].getCentralPosition();
	}

}
