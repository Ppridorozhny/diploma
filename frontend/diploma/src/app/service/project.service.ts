import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Project} from "../model/project";

@Injectable({providedIn: 'root'})
export class ProjectService {
  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<Project[]>('api/projects');
  }

  create(project: Project) {
    return this.http.post<Project>('/api/projects', project);
  }

  update(project: Project) {
    return this.http.put<Project>('/api/projects/' + project.id, project);
  }

  getById(id: number) {
    return this.http.get<Project>('api/projects/' + id);
  }

}
