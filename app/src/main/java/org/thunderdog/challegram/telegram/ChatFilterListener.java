package org.thunderdog.challegram.telegram;

import org.drinkless.td.libcore.telegram.TdApi;

public interface ChatFilterListener {
  void onUpdateChatFilter (TdApi.ChatFilterInfo[] updatedChatFilters);
}
