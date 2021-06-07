package mpei.vkr.ui.settings.items;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import mpei.vkr.ui.settings.items.masterkey.SettingsMasterKeyViewModel;

public class ModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SharedPreferences sp;

    public ModelFactory(SharedPreferences sp) {
        super();
        this.sp = sp;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == SettingsMasterKeyViewModel.class) {
            return (T) new SettingsMasterKeyViewModel(sp);
        }
        return null;
    }
}
