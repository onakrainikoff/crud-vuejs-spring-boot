import axios from 'axios';

const api = axios.create({
    baseURL: '/api',
    timeout: 1000
})

async function getProjects({ pageSize, pageNumber, sortBy, sortOrder, code, name, dateCreatedFrom, dateCreatedTo, dateUpdatedFrom, dateUpdatedTo }) {
    const response = await api({
        method: 'get',
        url: '/projects',
        params: {pageSize, pageNumber, sortBy, sortOrder, code, name, dateCreatedFrom, dateCreatedTo, dateUpdatedFrom, dateUpdatedTo}
    })
    return response.data
}

export default { getProjects }