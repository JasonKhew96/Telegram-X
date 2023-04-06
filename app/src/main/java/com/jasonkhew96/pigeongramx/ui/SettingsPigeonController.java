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
      case R.id.btn_disableCameraButton:
        PigeonSettings.instance().toggleDisableCameraButton();
        adapter.updateValuedSettingById(R.id.btn_disableCameraButton);
        break;
      case R.id.btn_disableRecordButton:
        PigeonSettings.instance().toggleDisableRecordButton();
        adapter.updateValuedSettingById(R.id.btn_disableRecordButton);
        break;
      case R.id.btn_rememberSendOptions:
        PigeonSettings.instance().toggleRememberSendOptions();
        adapter.updateValuedSettingById(R.id.btn_rememberSendOptions);
        break;
      case R.id.btn_disableStickerTimestamp:
        PigeonSettings.instance().toggleDisableStickerTimestamp();
        adapter.updateValuedSettingById(R.id.btn_disableStickerTimestamp);
        break;
      case R.id.btn_kofi: {
        tdlib.ui().openUrl(this, "https://ko-fi.com/jasonkhew96", new TdlibUi.UrlOpenParameters().forceInstantView());
        break;
      }
    }
  }

  @Override public void onApplySettings (int id, SparseIntArray result) {
  }

  @Override public int getId () {
    return R.id.controller_pigeonSettings;
  }

  @Override protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
        view.setDrawModifier(item.getDrawModifier());
        switch (item.getId()) {
          case R.id.btn_disableCameraButton:
            view.getToggler().setRadioEnabled(PigeonSettings.instance().isDisableCameraButton(), isUpdate);
            break;
          case R.id.btn_disableRecordButton:
            view.getToggler().setRadioEnabled(PigeonSettings.instance().isDisableRecordButton(), isUpdate);
            break;
          case R.id.btn_rememberSendOptions:
            view.getToggler().setRadioEnabled(PigeonSettings.instance().isRememberSendOptions(), isUpdate);
            break;
          case R.id.btn_disableStickerTimestamp:
            view.getToggler().setRadioEnabled(PigeonSettings.instance().isDisableStickerTimestamp(), isUpdate);
            break;
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableCameraButton, 0, R.string.DisableCameraButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableRecordButton, 0, R.string.DisableRecordButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rememberSendOptions, 0, R.string.RememberSendOptions));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableStickerTimestamp, 0, R.string.DisableStickerTimestamp));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_kofi, 0, R.string.Kofi));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    

    adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);
  }
}
