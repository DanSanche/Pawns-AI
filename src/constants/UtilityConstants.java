package constants;

/**
 * A collection of constants for the values assigned to particular game states
 * A loss is worth 0, so it will be avoided at all costs
 * A win is worth Integer.MAX_VALUE, so it will always trump any other value
 * A stale mate is one less than the win value, so it will be preferred to other states except for the win state
 * @author Sanche
 *
 */

public class UtilityConstants {
    public static final int WIN_VALUE = Integer.MAX_VALUE;
    public static final int LOSS_VALUE = 0;
    public static final int STALEMATE_VALUE = WIN_VALUE - 1;
}
