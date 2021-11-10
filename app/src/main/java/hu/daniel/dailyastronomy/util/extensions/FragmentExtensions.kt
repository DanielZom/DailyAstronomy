package hu.daniel.dailyastronomy.util.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import hu.daniel.dailyastronomy.R

fun Fragment.loadTo(fragmentManager: FragmentManager, containerId: Int = R.id.menuContainer) {
    fragmentManager
        .beginTransaction()
        .replace(containerId, this)
        .commit()
}