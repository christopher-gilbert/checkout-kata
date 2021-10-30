package net.gilbert.chris.checkout.mapper

import net.gilbert.chris.checkout.dto.Sterling

/**
 *
 * Class that takes an Integer quantity of an indeterminate currency and presents it as Sterling money
 * (100 pence to the pound)
 */
class SterlingMapper {

    fun penceToSterling(pence: Int) = Sterling(pence / 100, pence % 100)

}