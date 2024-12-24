package com.unbounded.realizingself.common

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@Composable
fun midPointCircleDraw(
    x_centre: Int,
    y_centre: Int, r: Int,
) {
    var x = r
    var y = 0

    // Printing the initial point
    // on the axes after translation
    print(
        "(" + (x + x_centre)
                + ", " + (y + y_centre) + ")"
    )

    // When radius is zero only a single
    // point will be printed
    if (r > 0) {
        print(
            ("(" + (x + x_centre)
                    + ", " + (-y + y_centre) + ")")
        )
        print(
            ("(" + (y + x_centre)
                    + ", " + (x + y_centre) + ")")
        )
        println(
            ("(" + (-y + x_centre)
                    + ", " + (x + y_centre) + ")")
        )
    }

    // Initialising the value of P
    var P = 1 - r
    while (x > y) {
        y++

        // Mid-point is inside or on the perimeter
        if (P <= 0) P = P + (2 * y) + 1 else {
            x--
            P = P + 2 * y - 2 * x + 1
        }

        // All the perimeter points have already
        // been printed
        if (x < y) break

        // Printing the generated point and its
        // reflection in the other octants after
        // translation
        print(
            ("(" + (x + x_centre)
                    + ", " + (y + y_centre) + ")")
        )
        print(
            ("(" + (-x + x_centre)
                    + ", " + (y + y_centre) + ")")
        )
        print(
            ("(" + (x + x_centre) +
                    ", " + (-y + y_centre) + ")")
        )
        println(
            ("(" + (-x + x_centre)
                    + ", " + (-y + y_centre) + ")")
        )

        // If the generated point is on the
        // line x = y then the perimeter points
        // have already been printed
        if (x != y) {
            print(
                ("(" + (y + x_centre)
                        + ", " + (x + y_centre) + ")")
            )
            print(
                ("(" + (-y + x_centre)
                        + ", " + (x + y_centre) + ")")
            )
            print(
                ("(" + (y + x_centre)
                        + ", " + (-x + y_centre) + ")")
            )
            println(
                ("(" + (-y + x_centre)
                        + ", " + (-x + y_centre) + ")")
            )
        }
    }
}

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true

}