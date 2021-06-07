package mpei.vkr.ui.settings.items;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import mpei.vkr.ui.settings.items.cipher.SettingsCipherViewModel;
import mpei.vkr.ui.settings.items.masterkey.SettingsMasterKeyViewModel;
import mpei.vkr.ui.settings.items.message.digest.SettingsMessageDigestViewModel;
import mpei.vkr.ui.settings.items.others.SettingsOthersViewModel;
import mpei.vkr.ui.settings.items.signature.SettingsSignatureViewModel;

public class ModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SharedPreferences sp;

    public ModelFactory(SharedPreferences sp) {
        super();
        this.sp = sp;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (SettingsMasterKeyViewModel.class.equals(modelClass)) {
            return (T) new SettingsMasterKeyViewModel(sp);
        } else if (SettingsOthersViewModel.class.equals(modelClass)) {
            return (T) new SettingsOthersViewModel(sp);
        } else if (SettingsCipherViewModel.class.equals(modelClass)) {
            return (T) new SettingsCipherViewModel();
        } else if (SettingsMessageDigestViewModel.class.equals(modelClass)) {
            return (T) new SettingsMessageDigestViewModel();
        } else if (SettingsSignatureViewModel.class.equals(modelClass)) {
            return (T) new SettingsSignatureViewModel();
        }
        return null;
    }
}
