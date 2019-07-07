from flask import Flask
from flask import request
from flask_cors import cross_origin
import requests
import json
import os
from swear import swear_de
import requests
import difflib

from my_nlp import words_to_check, get_tone, get_clean_sentance

# https://www.openthesaurus.de/synonyme/umbringen
# https://www.smileybedeutung.com/whatsapp-smileys-bedeutung/liste-smileys-menschen-whatsapp
# https://activewizards.com/blog/comparison-of-python-nlp-libraries/

import os
from flask import Flask, flash, request, redirect, jsonify
from werkzeug.utils import secure_filename

UPLOAD_FOLDER = os.getcwd()
ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

# @app.route("/")
# @cross_origin(headers=['Content-Type', 'Access-Control-Allow-Origin', 'Access-Control-Allow-Credentials'])
# def hello():
#     return "Hello World!"


'''
http://flask.pocoo.org/docs/1.0/api/?highlight=url_for#flask.url_for
Some what adapted.
Works with postman. Read this manual:
https://stackoverflow.com/questions/48607198/how-to-send-an-image-to-a-flask-server-using-postman
'''

from datetime import datetime

comments = []


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app.route('/', methods=['GET', 'POST'])
@cross_origin(headers=['Content-Type', 'Access-Control-Allow-Origin', 'Access-Control-Allow-Credentials'])
def post_text():
    print('Starting')
    if request.method == 'GET':
        return jsonify('GOT A GET REUEST')

    if request.method == 'POST':
        comment = request.data.decode("utf-8")
        comment = get_clean_sentance(comment)
        comment_set = {
            "time": datetime.now().strftime("%Y-%m-%d, %H:%M:%S"),
            "text": comment,
            "user": request.remote_addr
        }
        comments.insert(0, comment_set)
        return jsonify(comments)


if __name__ == '__main__':
    app.run('localhost', 4000, debug=True)
