import spacy
import json
from random import randrange

nlp = spacy.load('de_core_news_sm')
import boto3
import json

import re
import requests

comprehend = boto3.client(service_name='comprehend', region_name='eu-central-1')


# Excludes words, that can not be offenice
def words_to_check(text):
    doc = nlp(text)
    words = []
    for token in doc:
        print(token.text, token.pos_, token.dep_, end=";")
        if token.pos_ in ["PUNCT", "ADP", "DET", "PART", "ADV ROOT", "ADV"] or len(token.text) <= 3:
            continue
        words.append(token.text)
    print("")
    print(words)
    return words


def hasNumbers(inputString):
    return bool(re.search(r'\d', inputString))


# Get information, if the tone to extreme
def get_tone(comment):
    url = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze"

    querystring = {"key": "AIzaSyAWH2f0lJkfrBhz"+"t73vWB4APzPA4QACkfY"}

    payload = {"comment":
                   {"text": comment},
               "languages": ["de"],
               "requestedAttributes": {
                   "SEVERE_TOXICITY_EXPERIMENTAL": {},
                   "THREAT_EXPERIMENTAL": {}
               }
               }
    # print(payload)
    headers = {
        'Content-Type': "application/json",
    }
    response = requests.request("POST", url, data=json.dumps(payload), headers=headers, params=querystring)
    r = response.json()
    # print(r)
    attributs = r["attributeScores"]
    is_extrem = False
    for att in attributs:
        if attributs[att]["summaryScore"]["value"] > 0.60:
            print("Bad Value:", attributs[att]["summaryScore"]["value"])
            is_extrem = True
            break
    return is_extrem


def get_negative_words_aws(words):
    negative_words = []
    r = comprehend.batch_detect_sentiment(TextList=words, LanguageCode='de')
    i = 0
    # print(r)
    for a in r["ResultList"]:
        if a["Sentiment"] == "NEGATIVE":
            print("Bad Word:", words[i])
            negative_words.append(words[i])
        i += 1
    return negative_words


# Replaces highlighted words with emojies.
def replace_words_smilies(text, words):
    for word in words:
        text = text.replace(word, get_emojie(word))
    return text


# Replaces absolutely randomly words.
def replace_random(text):
    words = text.split(" ");
    word = words[randrange(len(words))]
    text = text.replace(word, get_emojie(word))
    return text


def get_emojie(word=""):
    emojies = ["🥦", "❤️", "😂", "💡","🍰","🦄"]
    return emojies[randrange(len(emojies))]


def get_clean_sentance(text):
    is_bad = get_tone(text)
    print(is_bad)
    while is_bad:
        words = words_to_check(text)
        words = get_negative_words_aws(words)
        if len(words) == 0:
            text = replace_random(text)
        else:
            text = replace_words_smilies(text, words)
        print("Neuer Satz:", text)
        is_bad = get_tone(text)
        print(is_bad)
    return text


if __name__ == '__main__':
    text = "Ich finde du bist ein echter Arschloch. Ich finde dich widerlich! Deine Mama fickt dich."
    text = get_clean_sentance(text)
    print (text)