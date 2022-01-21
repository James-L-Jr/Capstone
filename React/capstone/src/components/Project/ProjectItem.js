import React, { Component } from "react";

class ProjectItem extends Component {
  render() {
    return (
      <div>
        <h1>Project Item</h1>;
        <ProjectItem />
        <ProjectItem />
        <ProjectItem />
      </div>
    );
  }
}

export default ProjectItem;