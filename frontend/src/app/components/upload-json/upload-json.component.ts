import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FileUploader} from 'ng2-file-upload';

@Component({
  selector: 'app-upload-json',
  templateUrl: './upload-json.component.html',
  styleUrls: ['./upload-json.component.scss']
})
export class UploadJsonComponent implements OnInit {

  UPLOAD_URL = 'http://localhost:8080/uploadJson';
  public uploader: FileUploader = new FileUploader({url: this.UPLOAD_URL});

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
  }

}
