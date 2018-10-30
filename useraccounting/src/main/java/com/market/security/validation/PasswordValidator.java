package com.market.security.validation;

import com.market.security.validation.helpers.CharSequenceMatcher;
import com.market.security.validation.helpers.KeyboardSequenceMatcher;
import com.market.security.validation.helpers.RepeatMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class PasswordValidator {

  @Autowired
  CharSequenceMatcher charSequenceMatcher;

  @Autowired
  KeyboardSequenceMatcher keyboardSequenceMatcher;

  @Autowired
  RepeatMatcher repeatMatcher;

  private final Integer maxRepeatingSequence = 3;
  private final Integer passwdMinLength = 8;
  private final Integer maxKeyCombinationLength = 3;

  public int validatePassword(final String pwd) {

    return 0;
  }

  /**
   * Check the strength of a given password.
   * @param pwd password
   * @return
   */
  public int checkPwdStrength(final String pwd) {

    if (!checkPwdLength(pwd)) {
      return 10;
    }

    if (!checkPwdComplexity(pwd)) {
      return 11;
    }

    if (!checkCharSequence(pwd)) {
      return 12;
    }

    if (!checkForKeyboardCombinations(pwd)) {
      return 13;
    }

    if (!checkForRepeatingPatterns(pwd)) {
      return 14;
    }

    return 0;
  }

  private boolean checkPwdLength(final String pwd) {

    if (pwd.length() < passwdMinLength) {
      return false;
    }
    return true;
  }

  private boolean checkPwdComplexity(final String pwd) {
    int countDigit = 0;
    int upperCase = 0;
    int lowerCase = 0;
    int others = 0;
    for (int i = 0; i < pwd.length(); i++) {
      char c = pwd.charAt(i);
      if (Character.isDigit(c)) {
        countDigit = 1;
      } else if (Character.isUpperCase(c)) {
        upperCase = 1;
      } else if (Character.isLowerCase(c)) {
        lowerCase = 1;
      } else {
        others = 1; // Sonderzeichen
      }
    }
    if (countDigit + upperCase + lowerCase + others < 3) {
      return false;
    }
    return true;
  }

  private boolean checkCharSequence(final String pwd) {
    String match = charSequenceMatcher.match(pwd, maxKeyCombinationLength);
    if (!StringUtils.isEmpty(match)) {
      return false;
    }
    return true;
  }

  private boolean checkForKeyboardCombinations(final String pwd) {
    String match = keyboardSequenceMatcher.match(keyboardSequenceMatcher.qwertz, pwd,
        maxKeyCombinationLength);
    if (!StringUtils.isEmpty(match)) {
      return false;
    }
    return true;
  }

  private boolean checkForRepeatingPatterns(final String pwd) {
    String match = repeatMatcher.match(pwd, maxRepeatingSequence);
    if (!StringUtils.isEmpty(match)) {
      return false;
    }
    return true;
  }

}