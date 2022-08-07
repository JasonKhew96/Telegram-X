package com.jasonkhew96.pigeongramx.ui;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;

import com.jasonkhew96.pigeongramx.PigeonSettings;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.telegram.TdlibUi;
import org.thunderdog.challegram.ui.ListItem;
import org.thunderdog.challegram.ui.RecyclerViewController;
import org.thunderdog.challegram.ui.SettingsAdapter;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class SettingsPigeonController extends RecyclerViewController<Void> implements View.OnClickListener, ViewController.SettingsIntDelegate {
  private SettingsAdapter adapter;

  public SettingsPigeonController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override public CharSequence getName () {
    return Lang.getString(R.string.PigeonSettings);
  }

  @Override public void onClick (View v) {
    int id = v.getId();
    switch (id) {
      case R.id.btn_recentStickersCount:
        int count = PigeonSettings.instance().getRecentStickersCount();
        showSettings(id, new ListItem[] {new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers20, 0, "20", R.id.btn_recentStickersCount, count == 20), new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers40, 0, "40", R.id.btn_recentStickersCount, count == 40), new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers60, 0, "60", R.id.btn_recentStickersCount, count == 60), new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers80, 0, "80", R.id.btn_recentStickersCount, count == 80), new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers100, 0, "100", R.id.btn_recentStickersCount, count == 100)}, this);
        break;
      case R.id.btn_disableCameraButton:
        PigeonSettings.instance().toggleDisableCameraButton();
        adapter.updateValuedSettingById(R.id.btn_disableCameraButton);
        break;
      case R.id.btn_disableRecordButton:
        PigeonSettings.instance().toggleDisableRecordButton();
        adapter.updateValuedSettingById(R.id.btn_disableRecordButton);
        break;
      case R.id.btn_enableComments:
        PigeonSettings.instance().toggleEnableComments();
        adapter.updateValuedSettingById(R.id.btn_enableComments);
        break;
      case R.id.btn_kofi: {
        tdlib.ui().openUrl(this, "https://ko-fi.com/jasonkhew96", new TdlibUi.UrlOpenParameters().forceInstantView());
        break;
      }
    }
  }

  @Override public void onApplySettings (int id, SparseIntArray result) {
    switch (id) {
      case R.id.btn_recentStickersCount:
        int count = 0;
        switch (result.valueAt(0)) {
          case R.id.btn_recentStickers40:
            count = 40;
            break;
          case R.id.btn_recentStickers60:
            count = 60;
            break;
          case R.id.btn_recentStickers80:
            count = 80;
            break;
          case R.id.btn_recentStickers100:
            count = 100;
            break;
          default:
            count = 20;
        }
        PigeonSettings.instance().setRecentStickersCount(count);
        adapter.updateValuedSettingById(R.id.btn_recentStickersCount);
        break;
    }
  }

  @Override public int getId () {
    return R.id.controller_pigeonSettings;
  }

  @Override protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
        view.setDrawModifier(item.getDrawModifier());
        switch (item.getId()) {
          case R.id.btn_recentStickersCount:
            view.setData("" + PigeonSettings.instance().getRecentStickersCount());
            break;
          case R.id.btn_disableCameraButton:
            view.getToggler().setRadioEnabled(PigeonSettings.instance().isDisableCameraButton(), isUpdate);
            break;
          case R.id.btn_disableRecordButton:
            view.getToggler().setRadioEnabled(PigeonSettings.instance().isDisableRecordButton(), isUpdate);
            break;
          case R.id.btn_enableComments:
            view.getToggler().setRadioEnabled(PigeonSettings.instance().isCommentsEnabled(), isUpdate);
            break;
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_recentStickersCount, 0, R.string.RecentStickersCount));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableCameraButton, 0, R.string.DisableCameraButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableRecordButton, 0, R.string.DisableRecordButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_enableComments, 0, R.string.EnableComments));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_kofi, 0, R.string.Kofi));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    

    adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);
  }
}
