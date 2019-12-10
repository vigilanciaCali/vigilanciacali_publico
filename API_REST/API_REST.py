import os
from flask import Flask
from time import sleep
from flask import request, jsonify
import requests
import json
from concurrent.futures import ThreadPoolExecutor
from includes import *
import cv2
import timeit

# DOCS https://docs.python.org/3/library/concurrent.futures.html#concurrent.futures.ThreadPoolExecutor
executor = ThreadPoolExecutor(2)

app = Flask(__name__)



def send_post_data(data,url):
    print("send_post_data(data,url)")

    headers = {'content-type': 'application/json'}
    response = requests.post(url, data=json.dumps(data), headers=headers)


@app.route('/capture_post_test',methods=['POST'])
def capture_post_test():
    print("____________capture_post_test()___________\n")
    print(request.get_json(silent=True))
    print("\n")
    return jsonify(request.get_json(silent=True))



#______________________________________________________________________________________________________

@app.route('/anomalyDetetectionState',methods=['GET'])
def anomalyDetectionState():
    global flag_anl

    data={"flag_anl":flag_anl}
    return jsonify(data)



@app.route('/anomalyDetetection',methods=['GET','POST'])
def anomalyDetetection():
    global content_anl,flag_anl,video_transaction_id_anl

    print(request.get_json(silent=True))
    data=content_anl
   

    if request.method == 'POST':
     content_anl = request.get_json(silent=True)
     print("_________________________________________")
     print(content_anl['path_video_in'])
     print(content_anl['name_video_in'])
     print(content_anl['parameters'][0]['time_start'])
     print(content_anl['parameters'][0]['time_end'])
     print(content_anl['path_video_out'])
     print(content_anl['name_video_out'])
     print(content_anl['video_transaction_id'])    
     print("_________________________________________")

     path_video_in=content_anl['path_video_in']
     name_video_in=content_anl['name_video_in']

     
     time_start=int(content_anl['parameters'][0]['time_start'])
     time_end=int(content_anl['parameters'][0]['time_end'])
     path_video_out=content_anl['path_video_out']
     name_video_out=content_anl['name_video_out']
     video_transaction_id_anl=content_anl['video_transaction_id']

     flag_anl=False;
     executor.submit(anomalyDetetection_run,path_video_in,name_video_in,time_start,time_end,path_video_out,name_video_out)
     
    return jsonify(data)



def anomalyDetetection_run(path_video_in,name_video_in,time_start,time_end,path_video_out,name_video_out):
    

    print("starting execution anomaly detection algorithm")
    time_i = time.time()       

    name_video=name_video_in.split(".")[0]
    #delete previous video 
    if (len(os.listdir(g_c3d_intput_folder))!=0):
       os.system("rm "+g_c3d_intput_folder+"*")
     
    #copy video input to directory input of C3D
    time_start=sec2hms_str(time_start)
    time_end=sec2hms_str(time_end)

    cap = cv2.VideoCapture(path_video_in+name_video_in)
    fr=int(cap.get(cv2.CAP_PROP_FPS))
    print("Frame rate: "+str(fr))
  
    
    os.system("ffmpeg -r "+str(fr)+" -i "+path_video_in+name_video_in+" -ss "+time_start+" -to "+time_end+" "+g_c3d_intput_folder+name_video+".avi")
    
    '''     
    os.system("ffmpeg -r "+str(fr)+" -i "+path_video_in+name_video_in+" -ss "+time_start+" -to "+time_end+" -acodec copy -vcodec copy "+g_c3d_intput_folder+name_video+".avi")
    '''

    #executing C3D
    
    os.system("sh "+g_c3d_execute)
    
    #executing anl
    #name_video=name_video_in.split(".")[0]
    os.system(g_anl+" "+g_path_features_txt+name_video+"_C.txt "+g_path_scores+name_video+"_C.mat")
    #executing make_out_anl
    name_video_out=name_video_out.split(".")
    if(os.path.isfile(path_video_out+name_video_out[0]+".avi")):
       os.system("rm "+path_video_out+name_video_out[0]+".avi")
    if(os.path.isfile(path_video_out+name_video_out[0]+".mp4")):
       os.system("rm "+path_video_out+name_video_out[0]+".mp4")
    if(os.path.isfile(path_video_out+name_video_out[0]+".png")):
       os.system("rm "+path_video_out+name_video_out[0]+".png")


    os.system("sh make_out_anl.sh "+name_video+".avi"+" "+path_video_out+" "+name_video_out[0])  

    if(len(name_video_out)==2):
      ext=name_video_out[1]
      if(ext=="mp4"):
        #os.system("ffmpeg -i "+path_video_out+name_video_out[0]+".avi"+" -vcodec copy -acodec copy "+path_video_out+name_video_out[0]+".mp4");
        os.system("ffmpeg -i "+path_video_out+name_video_out[0]+".avi"+" -vcodec libx264 -acodec aac "+path_video_out+name_video_out[0]+".mp4");
        #ffmpeg -i 22052019201726.mp4 -vcodec libx264 -acodec aac output.mp4        
        os.system("rm "+path_video_out+name_video_out[0]+".avi")
 
    
    elapsed = time.time() - time_i
    print("elapsed anomaly detection: "+str(elapsed))
    print("ending execution anomaly detection algorithm")


    anomalyDetetection_end(100)


def anomalyDetetection_end(error_code):
  
    global flag_anl,video_transaction_id_anl
   
    print("anomalyDetetection_end()")
   
    data={"errorCode":error_code,"message":g_error_code_message[error_code],"videoTransactionId":video_transaction_id_anl}
    send_post_data(data,g_listener_anl)

    flag_anl=True
   
#___________________________________________________________________________________________________


#___________________________________________________________________________________________________

@app.route('/trackingState',methods=['GET'])
def trackingState():
    global flag_tr

    data={"flag_tr":flag_tr}
    return jsonify(data)


@app.route('/tracking',methods=['GET','POST'])
def tracking():
    global content_tr,flag_tr,video_transaction_id_tr

    data=content_tr

    if request.method == 'POST':
     content_tr = request.get_json(silent=True)
     '''     
     print(content_tr['path_video_in'])
     print(content_tr['name_video_in'])
     print(content_tr['parameters'][0]['time_start'])
     print(content_tr['parameters'][0]['x'])
     print(content_tr['parameters'][0]['y']) 
     print(content_tr['parameters'][0]['width'])
     print(content_tr['parameters'][0]['hight']) 
     print(content_tr['path_video_out'])
     print(content_tr['name_video_out'])
     print(content_tr['video_transaction_id'])     
     '''

     path_video_in=content_tr['path_video_in']
     name_video_in=content_tr['name_video_in']
     #time_start=content_tr['parameters'][0]['time_start']
     time_start=0
     x=content_tr['parameters'][0]['x'] 
     y=content_tr['parameters'][0]['y']
     width=content_tr['parameters'][0]['width']
     hight=content_tr['parameters'][0]['hight']
     path_video_out=content_tr['path_video_out']
     name_video_out=content_tr['name_video_out']
     video_transaction_id_tr=content_tr['video_transaction_id'];     

     flag_tr=False;
     executor.submit(tracking_run,path_video_in,name_video_in,time_start,x,y,width,hight,path_video_out,name_video_out)
     

    return jsonify(data)


def tracking_run(path_video_in,name_video_in,time_start,x,y,width,hight,path_video_out,name_video_out):

    
    print("starting execution tracking detection algorithm")
    
    '''
    for i in range(0, 4):
       print("executing tracking")
       sleep(1) 
    '''

    name_function="callback_tracking"
    print('sh executing_tracking.sh '+name_function+' '+path_video_in+name_video_in+' '+str(x)+' '+str(y)+' '+str(width)+' '+str(hight)+' '+str(time_start)+' '+path_video_out+name_video_out)
    os.system('sh executing_tracking.sh '+name_function+' '+path_video_in+' '+name_video_in+' '+str(x)+' '+str(y)+' '+str(width)+' '+str(hight)+' '+str(time_start)+' '+path_video_out+' '+name_video_out)



    name_video_in=name_video_in.split(".");
    name_video_out=name_video_out.split(".");
 
    os.system("rm "+path_video_out+name_video_out[0]+".mp4");
    print("ffmpeg -i "+path_video_out+name_video_in[0]+".avi"+" -vcodec libx264 -acodec aac "+path_video_out+name_video_out[0]+".mp4")
    os.system("ffmpeg -i "+path_video_out+name_video_in[0]+".avi"+" -vcodec libx264 -acodec aac "+path_video_out+name_video_out[0]+".mp4");
    os.system("rm "+path_video_out+name_video_in[0]+".avi")
     
    print("ending execution anomaly detection algorithm")
    
    tracking_end(100)



def tracking_end(error_code):

    global flag_tr,video_transaction_id_tr

    print("tracking_end()")

    data={"errorCode":error_code,"message":g_error_code_message[error_code],"videoTransactionId":video_transaction_id_tr}
    send_post_data(data,g_listener_tr)

    flag_tr=True




if __name__ == '__main__':

    global content_anl, flag_anl, video_transaction_id_anl, content_tr, flag_tr, video_transaction_id_tr, error_code
  
    content_anl=""
    flag_anl=True
    video_transaction_id_anl=""
    content_tr=""
    flag_tr=True
    video_transaction_id_tr=""
  
    error_code=0
    
  

    app.run(debug=False, port=5000) #run app in debug mode on port 5000