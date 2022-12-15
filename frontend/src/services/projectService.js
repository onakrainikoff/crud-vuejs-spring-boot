import axios from 'axios';

const api = axios.create({
    baseURL: '/api',
    timeout: 1000
})

async function getProjects({ pageSize, pageNumber, sortBy, sortOrderDesc, code, name, dateCreatedFrom, dateCreatedTo, dateUpdatedFrom, dateUpdatedTo }) {
    const response = await api({
        method: 'get',
        url: '/projects',
        params: {pageSize, pageNumber, sortBy, sortOrderDesc, code, name, dateCreatedFrom, dateCreatedTo, dateUpdatedFrom, dateUpdatedTo}
    })
    return response.data
}

async function addProject({code, name, description}){
    const response = await api({
        method: 'post',
        url: '/project',
        data: {
            code, name, description
        }
    })
    return response
}

async function editProject({id, code, name, description}){
    const response = await api({
        method: 'put',
        url: `/project/${id}`,
        data: {
            code, name, description
        }
    })
    return response
}

async function deleteProject({id}){
    const response = await api({
        method: 'delete',
        url: `/project/${id}`
    })
    return response
}

export default { getProjects, addProject, editProject, deleteProject }