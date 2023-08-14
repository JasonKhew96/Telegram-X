package org.thunderdog.challegram.telegram;

import org.drinkless.tdlib.TdApi;

public interface ChatFilterListener {
  void onUpdateChatFilter (TdApi.ChatFolderInfo[] updatedChatFilters);
}
