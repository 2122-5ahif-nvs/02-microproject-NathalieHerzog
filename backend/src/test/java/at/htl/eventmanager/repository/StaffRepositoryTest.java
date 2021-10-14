package at.htl.eventmanager.repository;

import at.htl.eventmanager.entity.Engagement;
import at.htl.eventmanager.entity.Staff;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class StaffRepositoryTest {
    @Inject
    StaffRepository staffRepository;

    Set<Engagement> engagements = new HashSet<>();

    @Test
    public void testCreateStaff()
    {
        staffRepository.clearTable();
        Staff staff = new Staff("createdName", 40.0, true, engagements);
        staffRepository.create(staff);
        assertThat(staffRepository.countStaff()).isEqualTo(1);
    }

    @Test
    public void testUpdateStaff()
    {
        staffRepository.clearTable();

        Staff staff = new Staff("updateName1", 50.0, true, engagements);
        Staff newStaff = new Staff("updateName2", 60.1, true, engagements);
        staff.setId((long) 1);
        newStaff.setId((long) 1);
        var test = staff.getId();
        var test2 = newStaff.getId();
        staffRepository.create(staff);
        staffRepository.updateStaff(newStaff);
        assertThat(staffRepository.getByName("updateName2").getName()).isEqualTo(newStaff.getName());
    }

    @Test
    public void testDeleteStaff()
    {
        staffRepository.clearTable();
        Staff staff = new Staff("name1", 70.2, true, engagements);
        staffRepository.create(staff);
        assertThat(staffRepository.count()).isEqualTo(1);
        staffRepository.deleteStaff(staffRepository.getByName(staff.getName()).getId());
        assertThat(staffRepository.count()).isEqualTo(0);
    }

    @Test
    public void testGetAllStaff()
    {
        staffRepository.clearTable();
        Staff staff = new Staff("name1", 44.3, true, engagements);
        Staff staff2 = new Staff("name2", 30.0, true, engagements);
        Staff staff3 = new Staff("name3", 0.0, false, engagements);
        LinkedList<Staff> list = new LinkedList<>();
        list.add(staff);
        list.add(staff2);
        list.add(staff3);
        staffRepository.create(staff);
        staffRepository.create(staff2);
        staffRepository.create(staff3);
        assertThat(staffRepository.getAll().size()).isEqualTo(list.size());
    }

    @Test
    public void testGetStaffByName()
    {
        staffRepository.clearTable();
        Staff staff = new Staff("name1", 40.1, true, engagements);
        Staff staff2 = new Staff("name2", 20.0, false,engagements);
        staffRepository.create(staff);
        staffRepository.create(staff2);
        assertThat(staffRepository.getByName("name2").getName()).isEqualTo(staff2.getName());
    }
}
