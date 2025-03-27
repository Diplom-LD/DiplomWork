import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {

    @Test
    fun testMockitoUsage() {
        // Arrange
        val mockList = mock<List<String>>()
        whenever(mockList.size).thenReturn(2)

        // Act
        val size = mockList.size

        // Assert
        verify(mockList).size
        assert(size == 2)
    }
}
