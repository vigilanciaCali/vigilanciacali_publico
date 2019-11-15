import os
from flask import Flask
from time import sleep
from flask import request, jsonify
import requests
import json
from concurrent.futures import ThreadPoolExecutor
from includes import *

# DOCS https://docs.python.org/3/library/concurrent.futures.html#concurrent.futures.ThreadPoolExecutor
executor = ThreadPoolExecutor(4)

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




@app.route('/anomalyDetetectionState',methods=['GET'])
def anomalyDetectionState():
    global content_anl,flag_anl

    data={"flag_anl":flag_anl}
    return jsonify(data)



@app.route('/anomalyDetetection',methods=['GET','POST'])
def anomalyDetetection():
    global content_anl,flag_anl

    data=content_anl

    if request.method == 'POST':
     content_anl = request.get_json(silent=True)
     
     print(content_anl['path_video_in'])
     print(content_anl['name_video_in'])
     print(content_anl['parameters']['time_start'])
     print(content_anl['parameters']['time_end'])
     print(content_anl['path_video_out'])
     print(content_anl['name_video_out'])
     
     path_video_in=content_anl['path_video_in']
     name_video_in=content_anl['name_video_in']
     time_start=content_anl['parameters']['time_start']
     time_end=content_anl['parameters']['time_end']
     path_video_out=content_anl['path_video_out']
     name_video_out=content_anl['name_video_out']

     flag_anl=False;
     executor.submit(anomalyDetetection_run,path_video_in,name_video_in,time_start,time_end,path_video_out,name_video_out)
     
    return jsonify(data)



def anomalyDetetection_run(path_video_in,name_video_in,time_start,time_end,path_video_out,name_video_out):
    
    print("starting execution anomaly detection algorithm")

    #delete previous video 
    os.system("rm "+g_c3d_intput_folder+"*")
    #copy video input to directory input of C3D
    time_start=sec2hms_str(time_start)
    time_end=sec2hms_str(time_end)
    os.system("ffmpeg -i "+path_video_in+name_video_in+" -ss "+time_start+" -to "+time_end+" -c copy "+g_c3d_intput_folder+name_video_in)
    #executing C3D
    os.system("sh "+g_c3d_execute)
    #executing anl
    name_video=name_video_in.split(".")[0]
    os.system(g_anl+" "+g_path_features_txt+name_video+"_C.txt "+g_path_scores+name_video+"_C.mat")
    #executing make_out_anl
    name_video_out=name_video_out.split(".")
    if(os.path.isfile(path_video_out+name_video_out[0]+".avi")):
       os.system("rm "+path_video_out+name_video_out[0]+".avi")
    if(os.path.isfile(path_video_out+name_video_out[0]+".mp4")):
       os.system("rm "+path_video_out+name_video_out[0]+".mp4")
    if(os.path.isfile(path_video_out+name_video_out[0]+".png")):
       os.system("rm "+path_video_out+name_video_out[0]+".png")


    os.system("sh make_out_anl.sh "+name_video_in+" "+path_video_out+" "+name_video_out[0])
    
    if(len(name_video_out)==2):
      ext=name_video_out[1]
      if(ext=="mp4"):
        os.system("ffmpeg -i "+path_video_out+name_video_out[0]+".avi"+" -vcodec copy -acodec copy "+path_video_out+name_video_out[0]+".mp4");
        os.system("rm "+path_video_out+name_video_out[0]+".avi")
 
    
          
    print("ending execution anomaly detection algorithm")


    anomalyDetetection_end(100)


def anomalyDetetection_end(error_code):
  
    global flag_anl
   
    print("anomalyDetetection_end()")
   
    data={"error_code":error_code,"message":g_error_code_message[error_code]}
    send_post_data(data,g_listener_anl)

    flag_anl=True
   

if __name__ == '__main__':

    global content_anl, flag_anl, error_code,data_anl_debug
    
    error_code=0
    content_anl=""
    data_anl_debug="";
    flag_anl=True


    app.run(debug=True, port=5000) #run app in debug mode on port 5000
