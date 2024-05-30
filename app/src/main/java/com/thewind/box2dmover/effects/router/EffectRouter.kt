package com.thewind.box2dmover.effects.router

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thewind.box2dmover.effects.main.EffectPage
import com.thewind.box2dmover.effects.module.EffectType
import com.thewind.box2dmover.effects.module.page.ballflybinding.BalloonFlyBindingPage
import com.thewind.box2dmover.effects.module.page.beziereditor.BezierParamEditorPage
import com.thewind.box2dmover.effects.module.page.beziereditor.preview.cps.BezierAnimatorPreviewComposePage
import com.thewind.box2dmover.effects.module.page.beziereditor.preview.BezierAnimatorPreviewPage
import com.thewind.box2dmover.effects.module.page.snowdrop.SnowDropPage

val LocalEffectNavigation = staticCompositionLocalOf<NavHostController> {
    error("nothing provider")
}

@Composable
fun EffectRouterPage() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalEffectNavigation provides navController) {
        NavHost(navController = navController, startDestination = "/page/select") {
            composable("/page/select") {
                EffectPage { page ->
                    navController.navigate(page)
                }
            }

            composable(
                EffectType.SnowDrop.router,
                enterTransition = slideInFromRight,
                exitTransition = slideOutToRight
            ) {
                SnowDropPage()
            }

            composable(
                EffectType.ComposeBezierPreview.router,
                enterTransition = slideInFromRight,
                exitTransition = slideOutToRight
            ) {
                BezierAnimatorPreviewComposePage()
            }

            composable(
                EffectType.BalloonFlyBinding.router,
                enterTransition = slideInFromRight,
                exitTransition = slideOutToRight
            ) {
                BalloonFlyBindingPage()
            }

            composable(
                EffectType.BezierEditor.router,
                enterTransition = slideInFromRight,
                exitTransition = slideOutToRight
            ) {
                BezierParamEditorPage()
            }

            composable(
                EffectType.BezierPreview.router
            ) {
                BezierAnimatorPreviewPage()
            }
        }
    }

}


val slideInFromRight: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    {
        slideInHorizontally(animationSpec = tween(durationMillis = 200), initialOffsetX = {
            it
        })
    }

val slideOutToRight: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    {
        slideOutHorizontally(animationSpec = tween(durationMillis = 200), targetOffsetX = { it })
    }