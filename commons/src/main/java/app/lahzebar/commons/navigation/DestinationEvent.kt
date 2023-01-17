package app.lahzebar.commons.navigation

import androidx.navigation.NavOptions

sealed class DestinationEvent(val navOptions: NavOptions?) {
    class Home(navOptions: NavOptions? = null) : DestinationEvent(navOptions)
    class Keypad(navOptions: NavOptions? = null) : DestinationEvent(navOptions)
    class Profile(navOptions: NavOptions? = null) : DestinationEvent(navOptions)
    class Login(navOptions: NavOptions? = null) : DestinationEvent(navOptions)
}
