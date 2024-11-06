package com.mustafin.ebooks.core.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mustafin.ebooks.mainFlow.ui.screens.allBooksScreen.AllBooksScreenView
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.HomeScreenView
import com.mustafin.ebooks.readerFlow.ui.screens.readerScreen.ReaderScreenView
import kotlinx.serialization.Serializable

// Экраны приложения для Type Safe Navigation
@Serializable
private object HomeScreen

@Serializable
private object AllBooksScreen

@Serializable
private data class ReaderScreen(val bookId: Int)

// Граф навигации
@Composable
fun NavigationGraph(lastBookId: Int?) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (lastBookId == null) HomeScreen else ReaderScreen(lastBookId)
    ) {
        // Главный экран
        composable<HomeScreen> {
            HomeScreenView(
                openReader = { bookId -> navController.navigate(ReaderScreen(bookId)) },
                openAllBooksScreen = { navController.navigate(AllBooksScreen) }
            )
        }

        // Экран с полным списком книг
        composable<AllBooksScreen> {
            AllBooksScreenView(
                popBackNavigationStack = { navController.popBackStack() },
                openReader = { bookId -> navController.navigate(ReaderScreen(bookId)) }
            )
        }

        // Экран читалки
        composable<ReaderScreen> {
            val args: ReaderScreen = it.toRoute()
            ReaderScreenView(
                args.bookId,
                openHomeScreen = { navController.navigate(HomeScreen) }
            )
        }
    }
}