@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    @Test
    public void testGetDocumentById() {
        DocumentEntity mockDocument = new DocumentEntity();
        mockDocument.setIdDocument(1L);
        mockDocument.setTitle("Document Test");

        when(documentRepository.findById(1L)).thenReturn(Optional.of(mockDocument));

        DocumentEntity document = documentService.getDocumentById(1L);
        assertEquals("Document Test", document.getTitle());
    }
}