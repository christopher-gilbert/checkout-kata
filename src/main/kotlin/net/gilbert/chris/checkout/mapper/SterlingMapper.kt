package net.gilbert.chris.checkout.mapper

import net.gilbert.chris.checkout.dto.Sterling

class SterlingMapper {

    fun penceToSterling(pence: Int) = Sterling(pence / 100, pence % 100)

}