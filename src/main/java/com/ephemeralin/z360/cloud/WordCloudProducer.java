package com.ephemeralin.z360.cloud;

import com.ephemeralin.z360.model.KeywordSet;
import com.kennycason.kumo.*;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizer.WordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;
import org.springframework.util.Base64Utils;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordCloudProducer {

    private KumoFont defaultFont;
    private ColorPalette defaultColorPalette1;
    private ColorPalette defaultColorPalette2;

    private class CommaWordTokenizer implements WordTokenizer {
        @Override
        public List<String> tokenize(String sentence) {
            return Arrays.asList(sentence.split(",[ ]*"));
        }
    }

    public WordCloudProducer() {
        this.defaultFont = new KumoFont(new Font(Font.SANS_SERIF, 0, 12));

        this.defaultColorPalette1 = new ColorPalette(new Color(0x710910), new Color(0xB60915), new Color(0xF1002F), new Color(0xF13C3A), new Color(0xF4777C), new Color(0xB68B97));
        this.defaultColorPalette2 = new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0x9DE2FF));
    }

    public List<String> createClouds(KeywordSet first, KeywordSet second, CloudType cloudType) {
        List <String> cloudList = new ArrayList<String>();
        List<String> words1 = new ArrayList<>();
        words1.add(first.getWords());
        List<String> words2 = new ArrayList<>();
        words2.add(second.getWords());

        switch (cloudType) {
            case rectangle:
                cloudList.add(createCloudRectangle(words1, CloudNumber.first));
                cloudList.add(createCloudRectangle(words2, CloudNumber.second));
                break;
            case polarityCloud:
                cloudList.add(createPolarityCloud(words1, words2));
                cloudList.add("");
                break;
            default:
                cloudList.add("");
                cloudList.add("");
        }
        return cloudList;
    }

    @Deprecated
    public String createCircularCloud(List<String> texts) {
        WordCloud wordCloud = prepareForCircularCloud();

        FrequencyAnalyzer frequencyAnalyzer = prepareFrequencyAnalyzer();
        List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(texts);

        return buildCloud(wordCloud, wordFrequencies);
    }

    public String createPolarityCloud(List<String> set1, List<String> set2) {
        PolarWordCloud wordCloud = prepareForPolarityCloud();

        FrequencyAnalyzer frequencyAnalyzer = prepareFrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(750);
        frequencyAnalyzer.setMinWordLength(4);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(set1);
        final List<WordFrequency> wordFrequencies2 = frequencyAnalyzer.load(set2);

        return buildCloud(wordCloud, wordFrequencies, wordFrequencies2);
    }

    public String createCloudRectangle(List<String> texts, CloudNumber cloudNumber) {
        WordCloud wordCloud = prepareForCloudRectangle(cloudNumber);

        FrequencyAnalyzer frequencyAnalyzer = prepareFrequencyAnalyzer();
        List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(texts);

        return buildCloud(wordCloud, wordFrequencies);
    }

    @Deprecated
    private WordCloud prepareForCircularCloud() {
        final Dimension dimension = new Dimension(600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(defaultColorPalette1);
        wordCloud.setFontScalar(new SqrtFontScalar(8, 60));
        wordCloud.setKumoFont(defaultFont);
        return wordCloud;
    }

    private PolarWordCloud prepareForPolarityCloud() {
        final Dimension dimension = new Dimension(600, 600);
        final PolarWordCloud wordCloud = new PolarWordCloud(dimension, CollisionMode.PIXEL_PERFECT, PolarBlendMode.BLUR);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(defaultColorPalette1);
        wordCloud.setColorPalette2(defaultColorPalette2);
        wordCloud.setFontScalar(new SqrtFontScalar(8, 60));
        wordCloud.setBackgroundColor(Color.BLACK);
        wordCloud.setKumoFont(defaultFont);
        return wordCloud;
    }

    private WordCloud prepareForCloudRectangle(CloudNumber cloudNumber) {
        final Dimension dimension = new Dimension(600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new RectangleBackground(dimension));
        if (cloudNumber == CloudNumber.first) {
            wordCloud.setColorPalette(defaultColorPalette1);
        } else {
            wordCloud.setColorPalette(defaultColorPalette2);
        }
        wordCloud.setFontScalar(new LinearFontScalar(12, 80));
        wordCloud.setKumoFont(defaultFont);
        return wordCloud;
    }

    private FrequencyAnalyzer prepareFrequencyAnalyzer() {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordTokenizer(new CommaWordTokenizer());
        return frequencyAnalyzer;
    }

    private String buildCloud(WordCloud wordCloud, List<WordFrequency> wordFrequencies) {
        wordCloud.build(wordFrequencies);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        wordCloud.writeToStreamAsPNG(stream);
        byte[] byteArray = stream.toByteArray();
        return Base64Utils.encodeToString(byteArray);
    }

    private String buildCloud(PolarWordCloud wordCloud, List<WordFrequency> wordFrequencies1, List<WordFrequency> wordFrequencies2) {
        wordCloud.build(wordFrequencies1, wordFrequencies2);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        wordCloud.writeToStreamAsPNG(stream);
        byte[] byteArray = stream.toByteArray();
        return Base64Utils.encodeToString(byteArray);
    }

    private enum CloudNumber {
        first, second;
    }

}
