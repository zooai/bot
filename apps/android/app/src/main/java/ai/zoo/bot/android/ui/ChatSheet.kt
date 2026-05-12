package ai.zoo.bot.android.ui

import androidx.compose.runtime.Composable
import ai.zoo.bot.android.MainViewModel
import ai.zoo.bot.android.ui.chat.ChatSheetContent

@Composable
fun ChatSheet(viewModel: MainViewModel) {
  ChatSheetContent(viewModel = viewModel)
}
