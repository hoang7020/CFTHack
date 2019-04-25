/*
 *   Copyright (C) 2018 SHUBHAM TYAGI
 *
 *    This file is part of Trivia Hack.
 *     Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License"); you may not
 *     use this file except in compliance with the License. You may obtain a copy of
 *     the License at
 *
 *     https://www.gnu.org/licenses/gpl-3.0
 *
 *    Trivia Hack is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Trivia Hack.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *
 */

package ai.loko.hk.ui.ocr;


import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.ArrayList;

import ai.loko.hk.ui.constants.Constant;
import ai.loko.hk.ui.utils.Logger;

public class TesseractImageTextReader {

    private static volatile TessBaseAPI api;
  //  private static volatile TesseractImageTextReader INSTANCE;

    public static TesseractImageTextReader geInstance(String language) {
        api = new TessBaseAPI();
        api.init(Constant.tesseractPath, language);
        api.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
        return new TesseractImageTextReader();
    }

    public String[] getTextFromBitmap(Bitmap src) {
        //TessBaseAPI api = new TessBaseAPI();
        //api.init(Constant.tesseractPath, language);
        //api.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);

        api.setImage(src);
        String textOnImage;

        //StringBuilder text=new StringBuilder();
        try {
            textOnImage = api.getUTF8Text();
           // api.end();
        } catch (Exception e) {
            Logger.logException(e);
            return new String[]{"Scan Failed:  Could not set up the detector!"};
        }

        if (textOnImage == null) {
            return new String[]{"Scan Failed:  Could not set up the detector!"};
        }

        String[] textOnScreenArray = textOnImage.split("\n");
        Log.d("TESSERACT", "getTextFromBitmap: " + textOnScreenArray);
        ArrayList<String> textOnScreen = new ArrayList<>();

        for (String s : textOnScreenArray) {
            if (!s.equals("")) {
                textOnScreen.add(s);
            }
        }
        int lineCount = textOnScreen.size();
        if (lineCount > 3) {
            StringBuilder question = new StringBuilder();
            for (int i = 0; i < lineCount - 3; i++) {
                question.append(textOnScreen.get(i) + " ");
            }
            return new String[]{question.toString(), textOnScreen.get(lineCount - 3), textOnScreen.get(lineCount - 2), textOnScreen.get(lineCount - 1), textOnImage};
        }
        return new String[]{"Scan Failed: Could not read options"};
    }
    }
