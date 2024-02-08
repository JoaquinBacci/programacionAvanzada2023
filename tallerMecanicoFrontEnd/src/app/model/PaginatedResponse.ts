export class PaginatedResponse<T> {
    content: T[];      
    totalPages: number; 
    totalElements: number; 
}