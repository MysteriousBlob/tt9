package io.github.sspanak.tt9.ime.modes;

import androidx.annotation.Nullable;

import io.github.sspanak.tt9.hacks.InputType;
import io.github.sspanak.tt9.ime.helpers.TextField;
import io.github.sspanak.tt9.languages.Language;
import io.github.sspanak.tt9.languages.LanguageKind;
import io.github.sspanak.tt9.preferences.settings.SettingsStore;

public class ModeKanji extends ModePinyin {
	protected ModeKanji(SettingsStore settings, Language lang, InputType inputType, TextField textField) {
		super(settings, lang, inputType, textField);
		NAME = language.getName().replace(" / ローマ字", "");
	}

	@Override
	public boolean changeLanguage(@Nullable Language newLanguage) {
		if (LanguageKind.isJapanese(newLanguage)) {
			setLanguage(newLanguage);
			return true;
		}

		return false;
	}

	@Override
	public boolean shouldAcceptPreviousSuggestion(int nextKey, boolean hold) {
		if (digitSequence.isEmpty()) {
			return false;
		}

		String nextSequence = digitSequence + (char)(nextKey + '0');
		if (nextSequence.endsWith(PUNCTUATION_SEQUENCE) && !predictions.noDbWords()) {
			return false;
		}

		return super.shouldAcceptPreviousSuggestion(nextKey, hold);
	}
}
