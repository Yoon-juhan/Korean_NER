import json
from keras_crf import CRFModel
from tensorflow.keras import Model
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.text import tokenizer_from_json
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.preprocessing.sequence import pad_sequences
import re
from collections import defaultdict

# 모델을 저장하고 불러오기 위해 정의
class CustomCRFModel(CRFModel):
    def __init__(self, model, units):
        super(CustomCRFModel, self).__init__(model, units)
        self.model = model
        self.units = units

    def get_config(self):
        config = {
            'model': self.model.get_config(),
            'units': self.units
        }
        return config

    @classmethod
    def from_config(cls, config):
        model = Model.from_config(config['model'])
        units = config['units']
        return cls(model, units)


def predict(text):
    custom_objects = {
        'CustomCRFModel': CustomCRFModel
    }

    # 저장된 모델 불러오기
    model = load_model('../model/bilstm_crf.h5', custom_objects=custom_objects, compile=False)
    model.compile(optimizer=Adam(0.001), metrics='accuracy')

    # 저장된 토크나이저 불러오기
    with open('../model/sentence_tokenizer_json.json', 'r', encoding='utf-8') as f:
        sentence_tokenizer_json = json.load(f)
        sentence_tokenizer = tokenizer_from_json(sentence_tokenizer_json)

    with open('../model/tag_tokenizer_json.json', 'r', encoding='utf-8') as f:
        tag_tokenizer_json = json.load(f)
        tag_tokenizer = tokenizer_from_json(tag_tokenizer_json)

    text = re.sub('[^ㄱ-ㅣ가-힣0-9a-zA-Z.]+', " ", text)
    text = text.split(" ")

    index_to_tag = tag_tokenizer.index_word

    X = sentence_tokenizer.texts_to_sequences([text])

    max_len = 70
    X = pad_sequences(X, padding='post', maxlen=max_len)

    y_predicted = model.predict(X)[0]

    tag = defaultdict(list)

    for i, pred in enumerate(y_predicted[0]):
        if pred not in [0, 1]: # PAD값은 제외
            print(f"{text[i]:17} {index_to_tag[pred]}")
            tag[index_to_tag[pred].split('-')[1]].append(text[i])
    
    return dict(tag)